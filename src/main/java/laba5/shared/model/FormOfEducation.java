package laba5.shared.model;

import java.io.Serializable;

/**
 * Перечисление форм обучения
 */
public enum FormOfEducation implements Serializable {
    /** Дистанционное обучение. */
    DISTANCE_EDUCATION,
    /** Полное обучение. */
    FULL_TIME_EDUCATION,
    /** Вечернее обучение. */
    EVENING_CLASSES;
}
