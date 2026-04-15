package planillaje.Vehicular.planillaje.vehicular.servicios;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.BadRequestException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.ForbiddenException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.VehiculoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.VehiculoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.ParqueaderoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;
import planillaje.Vehicular.planillaje.vehicular.mapper.VehiculoMapper;
import planillaje.Vehicular.planillaje.vehicular.respositorios.ParqueaderoRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.VehiculoRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ParqueaderoRepository parqueaderoRepository;
    private final CurrentService currentService;
    private final VehiculoMapper vehiculoMapper;


    public VehiculoService(VehiculoRepository vehiculoRepository, ParqueaderoRepository parqueaderoRepository,
                           CurrentService currentService, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.parqueaderoRepository = parqueaderoRepository;
        this.currentService = currentService;
        this.vehiculoMapper = vehiculoMapper;
    }

    @Transactional
    public VehiculoResponse registrarVehiculo(VehiculoRequest data) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        ParqueaderoEntity parqueadero = parqueaderoRepository.findById(data.getParqueaderoId()).orElseThrow(() -> new NotFoundException("Parqueadero no encontrado"));
    //Verificar que el vehiculo ya se encuentra registrado


        if (usuario.getPuesto() == null ||
                !parqueadero.getPuesto().getId().equals(usuario.getPuesto().getId())) {
            throw new BadRequestException("No tiene permisos para registrar vehículos en este puesto");
        }
        if (parqueadero.getEstado() == ParqueaderoEstado.OCUPADO) {
            throw new BadRequestException("Parqueadero ya ocupado");
        }

        VehiculoEntity vehiculo = VehiculoEntity.builder()
                .placa(data.getPlaca().toUpperCase())
                .marca(data.getMarca())
                .parqueadero(parqueadero)
                .build();

        parqueadero.setEstado(ParqueaderoEstado.OCUPADO);
        parqueaderoRepository.save(parqueadero);
        VehiculoEntity guardado = vehiculoRepository.save(vehiculo);

        return vehiculoMapper.vehiculoToResponse(guardado);
    }

    @Transactional
    public Page<VehiculoResponse> listarVehiculso(int page, int size) {
        //Obtener usuario autenticado
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();

        Pageable pageable = PageRequest.of(page, size);

        return vehiculoRepository.findByParqueadero_Puesto_Id(puesto.getId(), pageable).map(vehiculoMapper::vehiculoToResponse);
    }

    @Transactional
    public VehiculoResponse buscarVehiculoPorPlaca(String placa) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();

        VehiculoEntity vehiculo = vehiculoRepository.findByPlacaAndParqueadero_Puesto_Id(placa, puesto.getId()).orElseThrow(() -> new NotFoundException("Carro no registrado en este puesto"));

        return vehiculoMapper.vehiculoToResponse(vehiculo);
    }

    @Transactional
    public VehiculoResponse buscarPlaca(String placa) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();
        VehiculoEntity vehiculo = vehiculoRepository.findByPlaca(placa).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));

        return vehiculoMapper.vehiculoToResponse(vehiculo);
    }
}
