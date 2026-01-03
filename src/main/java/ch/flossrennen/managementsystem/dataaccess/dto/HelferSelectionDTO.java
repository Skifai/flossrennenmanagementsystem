package ch.flossrennen.managementsystem.dataaccess.dto;

import java.util.List;

public record HelferSelectionDTO(
        List<HelferDTO> zugewieseneHelfer,
        List<HelferDTO> verfuegbareHelfer
) {
}