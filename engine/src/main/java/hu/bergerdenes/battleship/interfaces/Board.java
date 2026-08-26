package hu.bergerdenes.battleship.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hu.bergerdenes.battleship.engine.Orientation;
import hu.bergerdenes.battleship.engine.Randomizer;
import hu.bergerdenes.battleship.engine.Ship;
import hu.bergerdenes.battleship.engine.ShipOnBoardConfiguration;

public class Board {

    private final int boardSize;
    private final Randomizer randomizer;
    private final List<Ship> ships = new ArrayList<>();
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
        shipConfigurations.forEach(this::placeShips);
    }

    /**
     * TODO: this should be dynamically determined based on board size
     */
    private List<ShipOnBoardConfiguration> determineShipConfiguration() {
        return List.of(
            new ShipOnBoardConfiguration(5, 1),
            new ShipOnBoardConfiguration(4, 2),
            new ShipOnBoardConfiguration(3, 3),
            new ShipOnBoardConfiguration(2, 2),
            new ShipOnBoardConfiguration(1, 1)
        );
    }

    private void placeShips(ShipOnBoardConfiguration cfg) {
        for (int i = 0; i < cfg.shipCount(); i++) {
            ships.add(placeShip(cfg.shipSize()));
        }
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
        return ships.stream().noneMatch(ship -> ship.isAdjacentOrOverlapping(probe));
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
