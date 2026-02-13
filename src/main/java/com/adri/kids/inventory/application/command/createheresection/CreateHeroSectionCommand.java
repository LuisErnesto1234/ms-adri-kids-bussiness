package com.adri.kids.inventory.application.command.createheresection;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.util.List;
import java.util.UUID;

public record CreateHeroSectionCommand(String title, String subtitle, String ctaText,
                                       String ctaUrl, List<String> imageUrls, GeneralStatus status)
    implements Command<UUID> {
}
