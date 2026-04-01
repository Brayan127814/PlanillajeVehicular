package planillaje.Vehicular.planillaje.vehicular.servicios;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.ParqueaderoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;
import planillaje.Vehicular.planillaje.vehicular.mapper.ParqueaderoMapper;
import planillaje.Vehicular.planillaje.vehicular.mapper.VehiculoMapper;
import planillaje.Vehicular.planillaje.vehicular.respositorios.ParqueaderoRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.VehiculoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParqueaderoService {
    private final ParqueaderoRepository parqueaderoRepository;
    private final PuestoRepository puestoRepository;
    private final CurrentService currentService;
    private final ParqueaderoMapper parqueaderoMapper;
    private final VehiculoRepository vehiculoRepository;

    public ParqueaderoService(ParqueaderoRepository parqueaderoRepository,
                              PuestoRepository puestoRepository, CurrentService currentService, ParqueaderoMapper parqueaderoMapper,
                              VehiculoRepository vehiculoRepository
    ) {
        this.parqueaderoRepository = parqueaderoRepository;
        this.puestoRepository = puestoRepository;
        this.currentService = currentService;
        this.parqueaderoMapper = parqueaderoMapper;
        this.vehiculoRepository = vehiculoRepository;
    }

    public void registrarParqueadero(ParqueaderoRequest data) {

        // BUSCAR PUESTO DEL PARQUEADERO
        PuestoEntity puesto = puestoRepository.findById(data.getPuestoId())
                .orElseThrow(() -> new NotFoundException("Puesto no encontrado"));

        // VALIDAR SI YA EXISTEN PARQUEADEROS EN ESE PUESTO
        if (parqueaderoRepository.existsByPuestoId(puesto.getId())) {
            throw new RuntimeException("El puesto ya tiene los parqueaderos registrados");
        }

        // CREAR LISTA DE PARQUEADEROS
        List<ParqueaderoEntity> lista = new ArrayList<>();

        for (int i = 1; i <= puesto.getTotalParqueaderos(); i++) {
            ParqueaderoEntity parqueadero = ParqueaderoEntity.builder()
                    .numeroParqueadero(i)
                    .estado(ParqueaderoEstado.VACIO)
                    .puesto(puesto)
                    .build();

            lista.add(parqueadero);
        }
        // GUARDAR TODOS LOS PARQUEADEROS EN LA BD
        parqueaderoRepository.saveAll(lista);
    }

    //Otener Ocupados
    public Page<ParqueaderoResponse> parqueaderosOcupados(int page, int size) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();

        Pageable pageable = PageRequest.of(page, size, Sort.by("numeroParqueadero").ascending());

        return parqueaderoRepository.findByPuestoAndEstadoOrderByNumeroParqueaderoAsc(usuario.getPuesto(), ParqueaderoEstado.OCUPADO, pageable).map(parqueaderoMapper::toResponse);
    }

    //PARQUEADEROS
    public Page<ParqueaderoResponse> paginados(int page, int size) {
        //Obtener usuario
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        //Obtner puesto
        PuestoEntity puesto = usuario.getPuesto();

        Pageable pageable = PageRequest.of(page, size, Sort.by("numeroParqueadero").ascending());

        return parqueaderoRepository.findByPuestoAndEstado(puesto, ParqueaderoEstado.VACIO, pageable).map(parqueaderoMapper::toResponse);
    }

    //LIBERAR UN PARQUEADERO
    @Transactional
    public void liberarParqueadero(Long parqueaderoID) {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        PuestoEntity puesto = usuario.getPuesto();
        //BUSCAR EL PARQUEADERO POR MEDIO DEL CARRO ASIGNADO
        ParqueaderoEntity parqueadero = parqueaderoRepository.findByIdAndPuesto_Id(parqueaderoID, puesto.getId()).orElseThrow(() -> new NotFoundException("Parqueadero no encontrado"));
        VehiculoEntity vehiculo = parqueadero.getVehiculo();
        System.out.println("VEHICULO: " + vehiculo.getPlaca());
        if (vehiculo != null) {
            vehiculo.setParqueadero(null);
            parqueadero.setVehiculo(null);

            vehiculoRepository.save(vehiculo);
        }

        parqueadero.setEstado(ParqueaderoEstado.VACIO);
        parqueaderoRepository.save(parqueadero);
    }
}
