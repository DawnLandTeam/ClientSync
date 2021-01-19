package cn.dawnland.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by cap_sub@dawnland.cn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer numberVersion;
    private boolean succeed;
    private String executeMsg;
    private LocalDateTime time;

}
