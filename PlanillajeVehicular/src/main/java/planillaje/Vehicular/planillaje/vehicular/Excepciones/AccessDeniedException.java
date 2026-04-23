package planillaje.Vehicular.planillaje.vehicular.Excepciones;

public class AccessDeniedException extends  RuntimeException{
     public  AccessDeniedException(String mensaje){
         super(mensaje);
     }
}
