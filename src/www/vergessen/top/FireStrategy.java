package www.vergessen.top;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    void fire(Tank tank);
}
