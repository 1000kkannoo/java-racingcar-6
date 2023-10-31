package racingcar.service;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RacingService {
    public List<Car> createCarList(String[] carNames) {
        List<Car> cars = new ArrayList<>();
        for (String carName : carNames) {
            cars.add(Car.createCar(carName));
        }
        return cars;
    }

    public List<Car> playSingleRound(List<Car> cars) {
        putRaceMove(cars);
        checkRoundWinner(cars);
        return cars;
    }

    private static void putRaceMove(List<Car> cars) {
        for (Car car : cars) {
            int move = Randoms.pickNumberInRange(0, 9);
            car.setMove(move);
        }
    }

    private static void checkRoundWinner(List<Car> cars) {
        int maxDistance = getMaxDistance(cars);
        for (Car car : cars) {
            plusWinnerCount(maxDistance, car);
        }
    }

    private static int getMaxDistance(List<Car> cars) {
        return cars.stream()
                .mapToInt(Car::getMove)
                .max()
                .orElse(0);
    }

    private static void plusWinnerCount(int max, Car car) {
        if (max == car.getMove()) {
            car.plusWinCount();
        }
    }

    public List<String> findByVictoryPlayers(List<Car> cars) {
        List<String> victoryCars = new ArrayList<>();
        cars.sort(Collections.reverseOrder(Comparator.comparing(Car::getWinCount)));
        Car vitoryCar = cars.get(0);

        for (Car car : cars) {
            checkVictoryPlayer(victoryCars, vitoryCar.getWinCount(), car);
        }

        return victoryCars;
    }

    private static void checkVictoryPlayer(List<String> victoryPlayers, int victoryCount, Car car) {
        int winCount = car.getWinCount();
        if (victoryCount == winCount) {
            victoryPlayers.add(car.getName());
        }
    }
}
