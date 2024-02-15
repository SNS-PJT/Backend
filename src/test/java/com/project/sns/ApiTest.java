package com.project.sns;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@MockBean(JpaMetamodelMappingContext.class)
public class ApiTest {

}
