package planillaje.Vehicular.planillaje.vehicular.servicios;

<<<<<<< HEAD
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
<<<<<<< HEAD
=======
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.mapper.PuestoMapper;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;

@Service
public class PuestoService {
    private final PuestoRepository puestoRepository;
    private final EmpresaRepository empresaRepository;
<<<<<<< HEAD

    public PuestoService(PuestoRepository puestoRepository, EmpresaRepository empresaRepository) {
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
=======
    private final PuestoMapper puestoMapper;
    private final CurrentService currentService;


    public PuestoService(PuestoRepository puestoRepository, EmpresaRepository empresaRepository, PuestoMapper puestoMapper, CurrentService currentService) {
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
        this.puestoMapper = puestoMapper;
        this.currentService = currentService;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    }


    public PuestoResponse registrarPuesto(PuestoRequest data) {
        // Buscar empresa
<<<<<<< HEAD
        EmpresaEntity empresa = empresaRepository.findById(data.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada"));

        PuestoEntity puesto = PuestoEntity.builder()
                .nombrePuesto(data.getNombrePuesto())
                .direccion(data.getNombrePuesto())
=======
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        EmpresaEntity empresa = empresaRepository.findById(usuario.getEmpresa().getId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada"));

        PuestoEntity puesto = PuestoEntity.builder()
                .nombrePuesto(data.getNombrePuesto())
                .direccion(data.getDireccion())
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
                .empresa(empresa)
                .totalParqueaderos(data.getTotalParqueaderos())
                .build();


        PuestoEntity guardado = puestoRepository.save(puesto);

<<<<<<< HEAD
        return PuestoResponse.builder()
                .id(guardado.getId())
                .nombrePuesto(guardado.getNombrePuesto())
                .direccion(guardado.getDireccion())
                .nombreEmpresa(empresa.getNombreEmpresa())
                .totalParqueaderos(guardado.getTotalParqueaderos())
                .build();
    }

=======
        return puestoMapper.puestoResponse(guardado);
    }
    //LISTAR PUESTOS DE UNA EMPRESA

    public Page<PuestoResponse> listarPuestos( int page, int size) {

        UsuarioEntity usuario = currentService.getCurrentUsuario();
        Pageable pageable = PageRequest.of(page, size);
        return puestoRepository.findByEmpresa_Id(usuario.getEmpresa().getId(), pageable).map(puestoMapper::puestoResponse);

    }


    /*
     PENDIENTES POR HACER
     LISTAR PUESTOS
     BUSCAR PUESTOS
     ELIMINAR PUESTOS
     OPCIONAL ACTUALIZAR
     */

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
