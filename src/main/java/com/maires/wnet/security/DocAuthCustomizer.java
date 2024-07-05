package com.maires.wnet.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;


/**
 * The type Doc auth customizer.
 */
@Component
public class DocAuthCustomizer implements OpenApiCustomizer {


  /**
   * The Scheme name.
   */
  @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName"})
  public final String schemeName = "Bearer Auth";

  @Override
  public void customise(OpenAPI openApi) {

    openApi.getComponents().addSecuritySchemes(schemeName,
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
    openApi.addSecurityItem(new SecurityRequirement().addList(schemeName));
  }
}