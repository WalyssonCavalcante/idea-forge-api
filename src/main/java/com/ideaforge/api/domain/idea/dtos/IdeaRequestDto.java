package com.ideaforge.api.domain.idea.dtos;

import java.util.List;

public record IdeaRequestDto(
   List<String> stacks,
   String experienceLevel,
   List<String> focusAreas
) {}
