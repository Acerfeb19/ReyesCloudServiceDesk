public class Technician extends User{
    public Technician(
        String userID, 
        String name,
        String email
    ) {
        super(userID, name, email);
    }

    @Override 
    public String getRole() {
        return "TECHNICIAN";
    }
}
