package cn.dawnland.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Created by cap_sub@dawnland.cn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionResponse {

    private String displayVersion;
    private Integer numberVersion;
    private String description;
    private boolean required;
    private String command;
    private Map<String, String> files;

}
