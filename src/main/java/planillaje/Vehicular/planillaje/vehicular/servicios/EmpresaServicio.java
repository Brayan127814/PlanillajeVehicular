package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;

@Service
public class EmpresaServicio {

    private EmpresaRepository empresaRepository;

    public EmpresaServicio(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /*
    Registrar una nueva empresa
     */

    public EmpresaResponse registrarEmpresas(EmpresaRequest data) {
        EmpresaEntity empresa = EmpresaEntity.builder()
                .nombreEmpresa(data.getNombreEmpresa())
                .nit(data.getNit())
                .build();

        EmpresaEntity guardado = empresaRepository.save(empresa);

        return EmpresaResponse.builder()
                .id(guardado.getId())
                .nombreEmpresa(guardado.getNombreEmpresa())
                .nit(guardado.getNit())
                .build();
    }
}
