package com.gsacorduvs.po;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.gsacorduvs.po");

        noClasses()
            .that()
            .resideInAnyPackage("com.gsacorduvs.po.service..")
            .or()
            .resideInAnyPackage("com.gsacorduvs.po.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.gsacorduvs.po.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
