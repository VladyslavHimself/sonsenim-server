package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.api.model.DeckConfigurationBody;
import com.sonsenim.sonsenimbackend.mappers.AggregatedDeckMapper;
import com.sonsenim.sonsenimbackend.mappers.DecksMapper;
import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.AggregatedDeckDTO;
import com.sonsenim.sonsenimbackend.model.dto.DeckDTO;
import com.sonsenim.sonsenimbackend.repositories.DecksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecksService {

    private final DecksRepository decksRepository;
    private final GroupsService groupsService;

    public DecksService(DecksRepository decksRepository, GroupsService groupsService) {
        this.decksRepository = decksRepository;
        this.groupsService = groupsService;
    }

    public List<DeckDTO> getUserDecksByGroupId(LocalUser user, Long groupId) {
        List<Deck> decks = decksRepository.findByGroups_IdAndGroups_LocalUser_Id(groupId, user.getId());
        return DecksMapper.toDeckDTOs(decks);
    }

    public void createNewDeck(LocalUser user, Long groupId, DeckConfigurationBody configuration) throws Exception {
        Groups group = groupsService.getUserGroupById(user, groupId);

        Deck deck = new Deck();
        deck.setDeckName(configuration.getDeckName());
        deck.setIsTyping(configuration.isTyping());
        deck.setIsFlashcardNormal(configuration.isFlashcardNormal());
        deck.setIsFlashcardReversed(configuration.isFlashcardReversed());
        deck.setRandomizedOrder(configuration.isRandomizedOrder());
        deck.setGroup(group);
        decksRepository.save(deck);
    }

    public List<AggregatedDeckDTO> getAggregatedDecksByGroupId(LocalUser user, Long groupId) {

        List<Deck> decks = decksRepository.findByGroups_IdAndGroups_LocalUser_Id(groupId, user.getId());

        return AggregatedDeckMapper.toAggregatedDeckDTOs(decks, user);
    }
}
