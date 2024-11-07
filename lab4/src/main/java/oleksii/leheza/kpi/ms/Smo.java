package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Smo {

    private List<Element> smoElements;

    public Smo(List<Element> smoElements) {
        this.smoElements = smoElements;
    }
}