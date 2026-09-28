public class Requester extends User {
    
    public Requester(
        String userID, 
        String name,
        String email
    ) {
        super(userID, name, email);
    }

    @Override 
    public String getRole() {
        return "Requester";
    }
}
