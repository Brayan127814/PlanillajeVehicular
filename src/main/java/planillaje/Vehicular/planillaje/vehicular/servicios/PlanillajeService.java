package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.BadRequestException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.*;
import planillaje.Vehicular.planillaje.vehicular.enums.NovedadesPlanillaje;
import planillaje.Vehicular.planillaje.vehicular.mapper.PlanillajeMapper;
import planillaje.Vehicular.planillaje.vehicular.mapper.VehiculoMapper;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PlanillajeVehicularRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.VehiculoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanillajeService {
    private final PlanillajeVehicularRepository planillajeVehicularRepository;
    private final CurrentService currentService;
    private final VehiculoRepository vehiculoRepository;
    private final PlanillajeMapper planillajeMapper;
    private final VehiculoMapper vehiculoMapper;

    public PlanillajeService(PlanillajeVehicularRepository planillajeVehicularRepository,
            CurrentService currentService, VehiculoRepository vehiculoRepository, PlanillajeMapper planillajeMapper,
            VehiculoMapper vehiculoMapper) {
        this.planillajeVehicularRepository = planillajeVehicularRepository;
        this.currentService = currentService;
        this.vehiculoRepository = vehiculoRepository;
        this.planillajeMapper = planillajeMapper;
        this.vehiculoMapper = vehiculoMapper;
    }

    // Registrar planillaje
    @Transactional
    public PlanillajeResponse registrarPlanillaje(PlanillajeRequest data) {
        // BUscar el usuario autenticado
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();
        // BUSCAR VEHICULO
        VehiculoEntity vehiculo = vehiculoRepository.findByPlacaForUpdate(data.getPlaca())
                .orElseThrow(() -> new NotFoundException("Vehiculo no registrado"));

        // Validar que no exista un planillaje registrado para este vehiculo el dia de
        // hoy
        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finDia = inicioDia.plusDays(1);

        if (planillajeVehicularRepository.existsByVehiculoAndHoraInicioGreaterThanEqualAndHoraInicioLessThan(vehiculo,
                inicioDia, finDia)) {
            throw new BadRequestException(
                    "Ya existe un planillaje registrado para este vehiculo el dia de hoy");
        }

        // Validar que el vehiculo tenga parqueadero asignado
        ParqueaderoEntity parqueadero = vehiculo.getParqueadero();
        if (parqueadero == null) {
            throw new RuntimeException("El vehiculo no tiene parqueadero asignado");
        }

        // Validar que el parqueadero asignado al vehiculo corresponda al puesto del
        // usuario autenticado

        if (!parqueadero.getPuesto().getId().equals(puesto.getId())) {
            throw new BadRequestException(
                    "Acceso denegado: este parqueadero pertenece al puesto ID " + parqueadero.getPuesto().getId());
        }
        /*
         * 
         * Validar novedades y detalle
         * 
         * Si novedades es OK, detalle debe ser null o vacio
         * Si novedades es OTRO, detalle no puede ser null ni vacio
         */

        if (data.getNovedadesPlanillaje() == NovedadesPlanillaje.OK
                && data.getDetalle() != null
                && !data.getDetalle().trim().isEmpty()) {
            throw new RuntimeException("Para novedades OK, el detalle debe ser null o vacio");
        }
        // Validar que si novedades es OTRO, detalle no sea null ni vacio

        if (data.getNovedadesPlanillaje() == NovedadesPlanillaje.OTRO && data.getDetalle() == null) {
            throw new RuntimeException("Se debe especificar un detalle para esta novedad");
        }

        // Crear el planillaje
        PlanillajeVehicularEntity planillaje = PlanillajeVehicularEntity.builder()
                .horaInicio(LocalDateTime.now())
                .puesto(puesto)
                .usuario(usuario)
                .vehiculo(vehiculo)
                .parqueadero(parqueadero)
                .novedades(data.getNovedadesPlanillaje())
                .detalle(data.getDetalle())
                .build();
        // Guardar las fotos si existen
        if (data.getFotoBase64() != null) {
            List<FotosPlanillajeEntity> fotos = data.getFotoBase64().stream().map(f -> FotosPlanillajeEntity.builder()
                    .fotoBase64(f)
                    .planillajeVehicularEntity(planillaje)
                    .build()

            ).toList();
            planillaje.setFotos(fotos);
        }

        PlanillajeVehicularEntity guardado = planillajeVehicularRepository.save(planillaje);

        return planillajeMapper.planillajeToResponse(guardado);

    }

   

    // Listar planillaje por placa con paginacion
    public Page<PlanillajeResponse> planillajePaginadosPlaca(String placa, int page, int size) {

        UsuarioEntity usuario = currentService.getCurrentUsuario();// usuario autenticado
        PuestoEntity puesto = usuario.getPuesto();//

        Pageable pageable = PageRequest.of(page, size);

        return planillajeVehicularRepository
                .buscarOptimizado(
                        placa,
                        puesto.getId(),
                        pageable)
                .map(planillajeMapper::planillajeToResponse);
    }

    // Listar planillaje por dia
    public Page<PlanillajeResponse> listarPlanillajePorDia(int page, int size) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();

        Pageable pageable = PageRequest.of(page, size);

        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finDia = inicioDia.plusDays(1);

        return planillajeVehicularRepository
                .findByPuesto_IdAndHoraInicioGreaterThanEqualAndHoraInicioLessThanOrderByHoraInicioDesc(
                        puesto.getId(), inicioDia, finDia, pageable)
                .map(planillajeMapper::planillajeToResponse);

    }
    // Contar planillaje por dia

    public long contarPlanillajePorDia(LocalDate fecha) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();

        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1);

        return planillajeVehicularRepository.countByPuesto_IdAndHoraInicioGreaterThanEqualAndHoraInicioLessThan(
                puesto.getId(), inicioDia, finDia);
    }

}
/*
 * PROXIMOS PASOS
 * CONSULTAR POR FECHAS DESDE HASTA
 * 
 */
