package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Smo {

    private List<SmoElement> smoElements = new ArrayList<>();

    public Smo(List<SmoElement> smoElements) {
        this.smoElements = smoElements;
    }
}
