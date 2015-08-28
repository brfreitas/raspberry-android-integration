package android.curso.com.br.rasperdroid.model;

/**
 * Created by Bruno on 28/08/2015.
 */
public class Dispositivo {
    public enum State{ ON, OFF};
    private String description;
    private Long id;
    private State state;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
