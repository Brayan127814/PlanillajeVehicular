package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.mapper.PuestoMapper;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;

@Service
public class PuestoService {
    private final PuestoRepository puestoRepository;
    private final EmpresaRepository empresaRepository;
    private final PuestoMapper puestoMapper;
    private final CurrentService currentService;


    public PuestoService(PuestoRepository puestoRepository, EmpresaRepository empresaRepository, PuestoMapper puestoMapper, CurrentService currentService) {
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
        this.puestoMapper = puestoMapper;
        this.currentService = currentService;
    }


    public PuestoResponse registrarPuesto(PuestoRequest data) {
        // Buscar empresa
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        EmpresaEntity empresa = empresaRepository.findById(usuario.getEmpresa().getId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada"));

        PuestoEntity puesto = PuestoEntity.builder()
                .nombrePuesto(data.getNombrePuesto())
                .direccion(data.getDireccion())
                .empresa(empresa)
                .totalParqueaderos(data.getTotalParqueaderos())
                .build();


        PuestoEntity guardado = puestoRepository.save(puesto);

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

}
