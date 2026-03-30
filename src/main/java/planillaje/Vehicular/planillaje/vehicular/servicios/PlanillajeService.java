package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
                             CurrentService currentService, VehiculoRepository vehiculoRepository, PlanillajeMapper planillajeMapper, VehiculoMapper vehiculoMapper) {
        this.planillajeVehicularRepository = planillajeVehicularRepository;
        this.currentService = currentService;
        this.vehiculoRepository = vehiculoRepository;
        this.planillajeMapper = planillajeMapper;
        this.vehiculoMapper = vehiculoMapper;
    }


    //Registrar planillaje
    public PlanillajeResponse registrarPlanillaje(PlanillajeRequest data) {
        //BUscar el usuario autenticado
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        //BUSCAR VEHICULO
        VehiculoEntity vehiculo = vehiculoRepository.findByPlaca(data.getPlaca()).orElseThrow(() -> new NotFoundException("Vehiculo no registrado"));
        //Obtener su parqueadero
        ParqueaderoEntity parqueadero = vehiculo.getParqueadero();
        if (parqueadero == null) {
            throw new RuntimeException("El vehiculo no tiene parqueadero asignado");
        }
        //PUESTO DEL PLANILLAJE
        PuestoEntity puesto = usuario.getPuesto();


        //validar puesto

        if (!parqueadero.getPuesto().getId().equals(puesto.getId())) {
            throw new BadRequestException("Acceso denegado: este parqueadero pertenece al puesto ID " + parqueadero.getPuesto().getId());
        }

        //VALIDAR EL DETALLE
        System.out.println("Novedades: " + data.getDetalle());
        System.out.println("Novedades Planillaje: " + data.getNovedadesPlanillaje());
        if (data.getNovedadesPlanillaje() == NovedadesPlanillaje.OK
                && data.getDetalle() != null
                && !data.getDetalle().trim().isEmpty()) {
            throw new RuntimeException("No se debe especificar nada en detalle");
        }

        if (data.getNovedadesPlanillaje() == NovedadesPlanillaje.OTRO && data.getDetalle() == null) {
            throw new RuntimeException("Debes especificar que novedad encontraste");
        }

        //REGISTRAR PLANILLAJE
        PlanillajeVehicularEntity planillaje = PlanillajeVehicularEntity.builder()
                .horaInicio(LocalDateTime.now())
                .puesto(puesto)
                .usuario(usuario)
                .vehiculo(vehiculo)
                .parqueadero(parqueadero)
                .novedades(data.getNovedadesPlanillaje())
                .detalle(data.getDetalle())
                .build();


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

    //Obtener el Historial del planillaje de un vhiculo

    public List<PlanillajeResponse> listarPlanillajePorPlaca(String placa) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();//usuario autenticado

        //Buscar todo el historiol de vehiculo
        VehiculoEntity vehiculo = vehiculoRepository.findByPlaca(placa).orElseThrow(() -> new NotFoundException("Vehculo no registrado"));

        List<PlanillajeVehicularEntity> lista = planillajeVehicularRepository.findByVehiculoOrderByHoraInicioDesc(vehiculo);


        if (lista.isEmpty()) {
            throw new NotFoundException("No hay registros de planillaje de este vehiculo con placas: " + placa);


        }
        return lista.stream().map(planillajeMapper::planillajeToResponse).toList();
    }

    public Page<PlanillajeResponse> planillajePaginadosPlaca(String placa, int page, int size) {

        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();

        Pageable pageable = PageRequest.of(page, size);

        return planillajeVehicularRepository
                .buscarOptimizado(
                        placa,
                        puesto.getId(),
                        pageable
                )
                .map(planillajeMapper::planillajeToResponse);
    }
}
    /*
    PROXIMOS PASOS
    CONSULTAR POR FECHAS DESDE HASTA

     */



