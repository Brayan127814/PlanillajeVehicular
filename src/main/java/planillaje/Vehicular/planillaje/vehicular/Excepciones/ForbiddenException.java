package planillaje.Vehicular.planillaje.vehicular.Excepciones;
/*
Excepción para acceso denegado
 */
public class ForbiddenException extends  RuntimeException{
      public  ForbiddenException(String mensaje){
           super(mensaje);
      }
}
