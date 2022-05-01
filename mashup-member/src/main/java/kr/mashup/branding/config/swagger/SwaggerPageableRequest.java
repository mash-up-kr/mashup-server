package kr.mashup.branding.config.swagger;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SwaggerPageableRequest {
    @ApiModelProperty(value = "Number of records per page", example = "20")
    private Integer size;

    @ApiModelProperty(value = "Results page you want to retrieve (0..N)", example = "0")
    private Integer page;

    @ApiModelProperty(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
    private String sort;
}

