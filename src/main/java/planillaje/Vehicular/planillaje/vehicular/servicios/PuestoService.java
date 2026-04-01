package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;

@Service
public class PuestoService {
    private final PuestoRepository puestoRepository;
    private final EmpresaRepository empresaRepository;

    public PuestoService(PuestoRepository puestoRepository, EmpresaRepository empresaRepository) {
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
    }


    public PuestoResponse registrarPuesto(PuestoRequest data) {
        // Buscar empresa
        EmpresaEntity empresa = empresaRepository.findById(data.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada"));

        PuestoEntity puesto = PuestoEntity.builder()
                .nombrePuesto(data.getNombrePuesto())
                .direccion(data.getNombrePuesto())
                .empresa(empresa)
                .totalParqueaderos(data.getTotalParqueaderos())
                .build();


        PuestoEntity guardado = puestoRepository.save(puesto);

        return PuestoResponse.builder()
                .id(guardado.getId())
                .nombrePuesto(guardado.getNombrePuesto())
                .direccion(guardado.getDireccion())
                .nombreEmpresa(empresa.getNombreEmpresa())
                .totalParqueaderos(guardado.getTotalParqueaderos())
                .build();
    }

    /*
     PENDIENTES POR HACER
     LISTAR PUESTOS
     BUSCAR PUESTOS
     ELIMINAR PUESTOS
     OPCIONAL ACTUALIZAR
     */

}
