package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.BadRequestException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.Interfaces.IntacionInterfaz;
import planillaje.Vehicular.planillaje.vehicular.dtos.InvitacionRequest;
import planillaje.Vehicular.planillaje.vehicular.entidades.InvitacionEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.InvitacionRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;

import java.util.UUID;

@Service
public class InvitacionServices implements IntacionInterfaz {

    private final InvitacionRepository invitacionRepository;
    private final CurrentService currentService;
    private final PuestoRepository puestoRepository;

    public InvitacionServices(InvitacionRepository invitacionRepository, CurrentService currentService, PuestoRepository puestoRepository) {
        this.invitacionRepository = invitacionRepository;
        this.currentService = currentService;
        this.puestoRepository = puestoRepository;
    }

    @Override
    public String crearInvitacion(InvitacionRequest puestoId) {

        //El obtener el id de la empresa mediante el admin logueado
        UsuarioEntity admin = currentService.getCurrentUsuario();

        //validar puesto
        PuestoEntity puesto = puestoRepository.findById(puestoId.getPuestoId()).orElseThrow(() -> new NotFoundException("Puesto no encontrado"));
        if (!puesto.getEmpresa().getId().equals(admin.getEmpresa().getId())) {
            throw new BadRequestException("Puesto no pertenece a tu empresa");

        }
        String token = UUID.randomUUID().toString();
        InvitacionEntity invitacion = InvitacionEntity.builder()
                .token(token)
                .empresaId(admin.getEmpresa().getId())
                .puestoId(puesto.getId())
                .usada(false)
                .build();

        invitacionRepository.save(invitacion);
        return token;
    }

    @Override
    public InvitacionEntity validarInvitacion(String token) {
        InvitacionEntity invitacion = invitacionRepository.findByToken(token).orElseThrow(() -> new BadRequestException("Token invalido"));

        if (invitacion.isUsada()) {
            throw new BadRequestException("Token ya fue usado");
        }
        return invitacion;
    }

    @Override
    public void marcarComoUsada(InvitacionEntity invitacion) {
        invitacion.setUsada(true);
        invitacionRepository.save(invitacion);
    }
}
