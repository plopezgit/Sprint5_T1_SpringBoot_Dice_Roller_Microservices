package com.plopez.diceroller.microservice.player.model.service;

import com.plopez.diceroller.microservice.player.model.dto.GameDTO;
import com.plopez.diceroller.microservice.player.model.dto.PlayerDTO;
import com.plopez.diceroller.microservice.player.model.entity.Player;
import com.plopez.diceroller.microservice.player.model.exception.NickNameAlreadyExistException;
import com.plopez.diceroller.microservice.player.model.exception.PlayerNotFoundException;
import com.plopez.diceroller.microservice.player.model.repository.PlayerRepository;
import com.plopez.diceroller.microservice.player.model.service.interfaces.GameClientServiceInterface;
import com.plopez.diceroller.microservice.player.model.service.interfaces.PlayerRankingInformationServiceInterface;
import com.plopez.diceroller.microservice.player.model.service.interfaces.PlayerServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService implements PlayerServiceInterface, GameClientServiceInterface, PlayerRankingInformationServiceInterface {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ModelMapper playerModelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<PlayerDTO> getPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map(this::getPlayerDTOFromEntity).collect(Collectors.toList());
    }

    @Override
    public PlayerDTO getPlayerBy(int id) {
        return playerRepository.findById(id).map(this::getPlayerDTOFromEntity)
                .orElseThrow(() -> new PlayerNotFoundException("The player does not exist"));
    }

    @Override
    public void createPlayer(PlayerDTO playerDTO) {
        PlayerDTO newPlayer = setNicknameTo(playerDTO);
        if (existNickname(newPlayer) && !isAnonymous(newPlayer))
            throw new NickNameAlreadyExistException("The nickname already exist, please try another one.");
        playerRepository.save(getPlayerEntityFromDTO(newPlayer));
    }

    @Override
    public void updatePlayerNickname(int id, PlayerDTO playerDTO) {
        PlayerDTO player = getPlayerBy(id);
        player.setNickname(playerDTO.getNickname());
        playerRepository.save(getPlayerEntityFromDTO(player));
    }

    @Override
    public void deletePlayerBy(int id) {
        playerRepository.deleteById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GameDTO> getGamesBy(int playerId) {
        return restTemplate.getForObject("http://game-service/games/player/" + playerId, List.class);
    }

    @Override
    public GameDTO createGameBy(int playerId) {
       if (!playerRepository.existsById(playerId)) {
           throw new PlayerNotFoundException();
       } else {
           return restTemplate.postForObject("http://game-service/games/"+ playerId +"/player", new GameDTO(), GameDTO.class);
       }
    }

    @Override
    public void updatePlayerSuccessRate(int playerId, float rate) {
        PlayerDTO player = getPlayerBy(playerId);
        player.setGameSuccessRate(rate);
        playerRepository.save(getPlayerEntityFromDTO(player));
    }

    @Override
    public void deleteGamesBy(int playerId) {
        restTemplate.delete("http://game-service/games/"+ playerId +"/delete");
    }

    @Override
    public float getTotalPlayersWinningAverage() {
        List<PlayerDTO> players = getPlayers();
        float sum = players.stream().map(PlayerDTO::getGameSuccessRate).reduce(0f, Float::sum);
        return sum / players.size();
    }

    @Override
    public Optional<PlayerDTO> getPlayerMostLoser() {
        return getPlayers().stream().min((Comparator.comparing(PlayerDTO::getGameSuccessRate)));
    }

    @Override
    public Optional<PlayerDTO> getPlayerMostWinner() {
        return getPlayers().stream().max((Comparator.comparing(PlayerDTO::getGameSuccessRate)));
    }

    private PlayerDTO getPlayerDTOFromEntity(Player player) {
        return playerModelMapper.map(player, PlayerDTO.class);
    }

    private Player getPlayerEntityFromDTO(PlayerDTO flowerDTO) {
        return playerModelMapper.map(flowerDTO, Player.class);
    }

    private boolean isNicknameEmptyOf(PlayerDTO playerToCheck) {
        return playerToCheck.getNickname().isEmpty() || playerToCheck.getNickname().isBlank();
    }

    private boolean existNickname(PlayerDTO playerToCheck) {
        return playerRepository.existsByNickname(playerToCheck.getNickname());
    }

    private boolean isAnonymous(PlayerDTO playerToCheck) {
        return playerToCheck.getNickname().equalsIgnoreCase("Anonymous");
    }

    private PlayerDTO setNicknameTo (PlayerDTO playerToSet) {
        return isNicknameEmptyOf(playerToSet) ?
                PlayerDTO.builder().nickname("Anonymous").build() :
                PlayerDTO.builder().nickname(playerToSet.getNickname()).build();
    }

}
