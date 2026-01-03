package ch.flossrennen.managementsystem.config;

import ch.flossrennen.managementsystem.dataaccess.dto.*;
import ch.flossrennen.managementsystem.dataaccess.mapper.*;
import ch.flossrennen.managementsystem.dataaccess.persistence.model.*;
import ch.flossrennen.managementsystem.dataaccess.persistence.repository.*;
import ch.flossrennen.managementsystem.service.validation.BenutzerValidator;
import ch.flossrennen.managementsystem.service.validation.EinsatzValidator;
import ch.flossrennen.managementsystem.service.validation.HelferValidator;
import ch.flossrennen.managementsystem.service.validation.RessortValidator;
import ch.flossrennen.managementsystem.util.CheckResult;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class NativeRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // Register the resource bundle for translation
        hints.resources().registerResourceBundle("translation/texts");

        // Register DTOs for reflection (required for Vaadin Binder and reflection-based components)
        hints.reflection().registerType(BenutzerDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(EinsatzDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(EinsatzZuweisungDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(HelferDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(HelferSelectionDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(LogDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(RessortDTO.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);

        // Register Entities for reflection
        hints.reflection().registerType(Benutzer.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(Einsatz.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(EinsatzZuweisung.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(Helfer.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(LogEntry.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
        hints.reflection().registerType(Ressort.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);

        // Register Repositories for reflection
        hints.reflection().registerType(BenutzerRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzZuweisungRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(HelferRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(LogRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(RessortRepository.class, MemberCategory.INVOKE_PUBLIC_METHODS);

        // Register Mappers for reflection
        hints.reflection().registerType(BenutzerDTOMapper.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzDTOMapper.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(HelferDTOMapper.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(LogDTOMapper.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(RessortDTOMapper.class, MemberCategory.INVOKE_PUBLIC_METHODS);

        // Register Validators for reflection
        hints.reflection().registerType(BenutzerValidator.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzValidator.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(HelferValidator.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(RessortValidator.class, MemberCategory.INVOKE_PUBLIC_METHODS);

        // Register Enums for reflection
        hints.reflection().registerType(BenutzerRolle.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzStatus.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(LogLevel.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(LogType.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(BenutzerDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(EinsatzZuweisungDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(HelferDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(LogDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);
        hints.reflection().registerType(RessortDTOProperties.class, MemberCategory.INVOKE_PUBLIC_METHODS);

        // Register Utility classes
        hints.reflection().registerType(CheckResult.class, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);

    }
}
