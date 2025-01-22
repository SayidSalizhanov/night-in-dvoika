package ru.itis.nightindvoika.util;

import ru.itis.nightindvoika.entites.AttackEntity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class StringCreator implements Serializable {

    public static String createPathImage(List<AttackEntity> attackEntities, int position) {
        StringBuilder imageName = new StringBuilder("pic");

        StringBuilder firstLettersFromEntitiesNames = new StringBuilder();

        // цикл для поиска сущностей, которые в данный момент находятся на позиции
        for (AttackEntity attackEntity : attackEntities) {

            if (attackEntity.getCurrentPosition() == position) {
                String className = attackEntity.getClass().getSimpleName();

                firstLettersFromEntitiesNames.append(className.toLowerCase().charAt(0));
            }
        }

        char[] letters = firstLettersFromEntitiesNames.toString().toCharArray();
        Arrays.sort(letters);

        return imageName.append(letters).toString();
    }
}
