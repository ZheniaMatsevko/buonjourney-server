package org.naukma.buonjourneyserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.service.IPackingListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/packing")
@RequiredArgsConstructor
public class PackingListController {

    private final IPackingListService packingListService;
}