package vn.yotel.jobsearch247.core.helper;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import vn.yotel.jobsearch247.core.model.BaseChoice;

import java.util.ArrayList;
import java.util.List;

public class BaseChoiceHelper {
    public static <T extends BaseChoice> List<BaseChoice> filter(List<T> source, BaseChoiceFilter filterMapping) {
        List<BaseChoice> baseChoices = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return baseChoices;
        }
        source.forEach((choice) -> {
            if (filterMapping.filter(choice)) {
                BaseChoice baseChoice = new BaseChoice();
                BeanUtils.copyProperties(choice, baseChoice);
                baseChoices.add(baseChoice);
            }
        });
        return baseChoices;
    }

    public interface BaseChoiceFilter<T extends BaseChoice> {
        boolean filter(T choice);
    }

    public static List<BaseChoice> buildList(List<Object[]> source) {
        List<BaseChoice> baseChoices = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return baseChoices;
        }

        for (Object[] objects : source) {
            BaseChoice baseChoice = new BaseChoice();
            baseChoice.setValue(String.valueOf(objects[0]));
            baseChoice.setLabel(String.valueOf(objects[1]));
            baseChoices.add(baseChoice);
        }
        return baseChoices;
    }

    public static <T extends BaseChoice, K> List<T> buildList(List<K> source, BaseChoiceMapper<T, K> mapper) throws Exception {
        List<T> baseChoices = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return baseChoices;
        }
        for (K object : source) {
            T option = (T) mapper.parse(object);
            baseChoices.add(option);
        }
        return baseChoices;
    }

    public interface BaseChoiceMapper<T extends BaseChoice, K> {
        T parse(K source) throws Exception;
    }
}
