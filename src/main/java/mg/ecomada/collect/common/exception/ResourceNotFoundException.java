package mg.ecomada.collect.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " non trouvé(e) avec l'id : " + id);
    }
}
