package projeto_final.cards.controller.dto;
import java.math.BigDecimal;
import projeto_final.cards.model.Feature;
public record FeatureDto(Long id, String icon, String description) {
    public FeatureDto(Feature feature){
        this(feature.getId(), feature.getIcon(), feature.getDescription());
    }
    public Feature toFeature(){
        Feature feature = new Feature();
        feature.setId(this.id);
        feature.setIcon(this.icon);
        feature.setDescription(this.description);
        return feature;
    }
}
