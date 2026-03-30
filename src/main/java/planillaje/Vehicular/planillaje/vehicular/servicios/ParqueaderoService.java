package planillaje.Vehicular.planillaje.vehicular.servicios;

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
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;
import planillaje.Vehicular.planillaje.vehicular.mapper.ParqueaderoMapper;
import planillaje.Vehicular.planillaje.vehicular.respositorios.ParqueaderoRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParqueaderoService {
    private final ParqueaderoRepository parqueaderoRepository;
    private final PuestoRepository puestoRepository;
    private final CurrentService currentService;
    private final ParqueaderoMapper parqueaderoMapper;

    public ParqueaderoService(ParqueaderoRepository parqueaderoRepository,
                              PuestoRepository puestoRepository, CurrentService currentService, ParqueaderoMapper parqueaderoMapper
    ) {
        this.parqueaderoRepository = parqueaderoRepository;
        this.puestoRepository = puestoRepository;
        this.currentService = currentService;
        this.parqueaderoMapper = parqueaderoMapper;
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

    //Otener disponibles
    public List<ParqueaderoResponse> parqueaderosDisponibles() {
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        List<ParqueaderoEntity> libres = parqueaderoRepository.findByPuestoAndEstadoOrderByNumeroParqueaderoAsc(usuario.getPuesto(), ParqueaderoEstado.VACIO);
        return libres.stream().map(p -> ParqueaderoResponse.builder()
                .id(p.getId())
                .nombrePuesto(p.getPuesto().getNombrePuesto())
                .numeroParqueadero(p.getNumeroParqueadero())
                .estado(p.getEstado())


                .build()).toList();
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
}
