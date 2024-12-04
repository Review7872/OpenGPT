package online.fadai.opengptproject.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import online.fadai.opengptproject.exception.ModelInvalidException;

@Getter
@RequiredArgsConstructor
public enum ModelType {

    /**
     * 通义千问
     */
    QWEN("qwen2.5:latest", 1),

    /**
     * 扁鹊
     */
    BIANQUE("bq:latest", 2);

    private final String modelName;  // 模型名称
    private final Integer modelType; // 模型类型编号

    public static void checkValidModelName(String name) {
        for (ModelType type : ModelType.values()) {
            if (type.modelName.equals(name)) {
                return;
            }
        }
        throw new ModelInvalidException();
    }

    public static String getModelName(Integer type) {
        for (ModelType modelType : ModelType.values()) {
            if (modelType.modelType.equals(type)) {
                return modelType.modelName;
            }
        }
        throw new ModelInvalidException();
    }

    public static ModelType getModel(String name) {
        for (ModelType modelType : ModelType.values()) {
            if (modelType.modelName.equals(name)) {
                return modelType;
            }
        }
        throw new ModelInvalidException();
    }

}

