package hu.bergerdenes.battleship.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private final int boardSize;
    private final Randomizer randomizer;
    private List<Ship> ships = new ArrayList<>();
    private int sankShips = 0;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.randomizer = new Randomizer(boardSize);
        determineShipPlacement();
    }

    public ShotResult shoot(Point hitPoint) {
        for (Ship ship : this.ships) {
            ShipShotResult shipShotResult = ship.checkHit(hitPoint);
            if (shipShotResult.hit()) {
                if (shipShotResult.sank()) {
                    sankShips++;
                }
                return new ShotResult(shipShotResult, sankShips == ships.size());
            }
        }
        return new ShotResult(new ShipShotResult(null, 0, null, null, false, false), sankShips == ships.size());
    }

    private void determineShipPlacement() {
        List<ShipOnBoardConfiguration> shipConfigurations = determineShipConfiguration();
        this.ships = shipConfigurations.stream()
            .flatMap(cfg -> placeShips(cfg).stream())
            .toList();
    }

    /**
     * TODO: this should be dynamically determined based on board size
     */
    private List<ShipOnBoardConfiguration> determineShipConfiguration() {
        return List.of(
//            new ShipOnBoardConfiguration(5, 1),
//            new ShipOnBoardConfiguration(4, 2),
//            new ShipOnBoardConfiguration(3, 3),
//            new ShipOnBoardConfiguration(2, 2),
            new ShipOnBoardConfiguration(1, 1)
        );
    }

    private List<Ship> placeShips(ShipOnBoardConfiguration cfg) {
        List<Ship> result = new ArrayList<>();
        for (int i = 0; i < cfg.shipCount(); i++) {
            result.add(placeShip(cfg.shipSize()));
        }
        return List.copyOf(result);
    }

    private Ship placeShip(int shipSize) {
        Ship probe;
        boolean isPlaceable;
        do {
            Orientation orientation = randomizer.getRandomOrientation();
            probe = new Ship(randomizer.getRandomPoint(shipSize, orientation), shipSize, orientation);
            isPlaceable = checkShipPlaceable(probe);
        } while (!isPlaceable);
        return probe;
    }

    private boolean checkShipPlaceable(Ship probe) {
        return ships.stream().noneMatch(ship -> ship.hasCollision(probe));
    }

    @Override
    public String toString() {
        String shipPrettyPrinted = ships.stream()
            .map(ship ->"\t\t" + ship.toString())
            .collect(Collectors.joining("\n"));
        return "Board{\n" +
            "\tboardSize=" + boardSize +
            ",\n\tships=\n" + shipPrettyPrinted +
            "\n}";
    }
}
