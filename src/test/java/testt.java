import model.Produit;  
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class testt {
    public static void main(String[] args) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();

            session.beginTransaction();

            Produit produit = new Produit("My First Product", 49.99);  // ← Changed to Produit
            session.save(produit);

            
            session.getTransaction().commit();

            System.out.println(" Product saved with ID: " + produit.getId());

            
            session.close();
            factory.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}