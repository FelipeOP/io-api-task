package com.epam.rd.autocode.assessment.basics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.epam.rd.autocode.assessment.basics.entity.BodyType;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.Vehicle;

public class Mapper {

    Mapper() {
    }

    private static void setStringToObjectFeatures(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                continue;
            }
            if (values[i].equals("\"\"")) {
                values[i] = "";
            } else if (values[i].isEmpty()) {
                values[i] = null;
            } else if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                values[i] = values[i].substring(1, values[i].length() - 1);
            }
        }
    }

    public static Client csvToClient(String[] values) {
        setStringToObjectFeatures(values);

        Client client = new Client();
        client.setId(values[0] != null && !values[0].isEmpty() ? Long.parseLong(values[0]) : 0);
        client.setEmail(values[1]);
        client.setPassword(values[2]);
        client.setName(values[3]);
        client.setBalance(values[4] != null && !values[4].isEmpty() ? new BigDecimal(values[4]) : null);
        client.setDriverLicense(values[5]);

        return client;
    }

    public static Employee csvToEmployee(String[] values) {
        setStringToObjectFeatures(values);

        Employee employee = new Employee();
        employee.setId(values[0] != null && !values[0].isEmpty() ? Long.parseLong(values[0]) : 0);
        employee.setEmail(values[1]);
        employee.setPassword(values[2]);
        employee.setName(values[3]);
        employee.setPhone(values[4]);
        employee.setDateOfBirth(values[5] != null && !values[5].isEmpty() ? LocalDate.parse(values[5]) : null);

        return employee;
    }

    public static Vehicle csvToVehicle(String[] values) {
        setStringToObjectFeatures(values);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(values[0] != null && !values[0].isEmpty() ? Long.parseLong(values[0]) : 0);
        vehicle.setMake(values[1]);
        vehicle.setModel(values[2]);
        vehicle.setCharacteristics(values[3]);
        vehicle.setYearOfProduction(values[4] != null && !values[4].isEmpty() ? Integer.parseInt(values[4]) : 0);
        vehicle.setOdometer(values[5] != null && !values[5].isEmpty() ? Long.parseLong(values[5]) : 0);
        vehicle.setColor(values[6]);
        vehicle.setLicensePlate(values[7]);
        vehicle.setNumberOfSeats(values[8] != null && !values[8].isEmpty() ? Integer.parseInt(values[8]) : 0);
        vehicle.setPrice(values[9] != null && !values[9].isEmpty() ? new BigDecimal(values[9]) : null);
        vehicle.setRequiredLicense(values[10] != null && !values[10].isEmpty() ? values[10].charAt(0) : (char) 0);
        vehicle.setBodyType(values[11] != null && !values[11].isEmpty() ? BodyType.valueOf(values[11]) : null);

        return vehicle;
    }

    public static Order csvToOrder(String[] values) {
        setStringToObjectFeatures(values);

        Order order = new Order();
        order.setId(values[0] != null && !values[0].isEmpty() ? Long.parseLong(values[0]) : 0);
        order.setClientId(values[1] != null && !values[1].isEmpty() ? Long.parseLong(values[1]) : 0);
        order.setEmployeeId(values[2] != null && !values[2].isEmpty() ? Long.parseLong(values[2]) : 0);
        order.setVehicleId(values[3] != null && !values[3].isEmpty() ? Long.parseLong(values[3]) : 0);
        order.setStartTime(values[4] != null && !values[4].isEmpty() ? LocalDateTime.parse(values[4]) : null);
        order.setEndTime(values[5] != null && !values[5].isEmpty() ? LocalDateTime.parse(values[5]) : null);
        order.setPrice(values[6] != null && !values[6].isEmpty() ? new BigDecimal(values[6]) : null);

        return order;
    }

    public static String[] orderToCsv(Order order) {
        String[] row = new String[7];
        row[0] = String.valueOf(order.getId());
        row[1] = String.valueOf(order.getClientId());
        row[2] = String.valueOf(order.getEmployeeId());
        row[3] = String.valueOf(order.getVehicleId());
        row[4] = order.getStartTime() != null && !order.getStartTime().toString().isEmpty()
                ? order.getStartTime().toString()
                : null;
        row[5] = order.getEndTime() != null && !order.getEndTime().toString().isEmpty()
                ? order.getEndTime().toString()
                : null;
        row[6] = order.getPrice() != null && !order.getPrice().toString().isEmpty()
                ? order.getPrice().toString()
                : null;

        return row;
    }

    public static String[] vehicleToCsv(Vehicle vehicle) {
        String[] row = new String[12];

        row[0] = String.valueOf(vehicle.getId());
        row[1] = vehicle.getMake();
        row[2] = vehicle.getModel();
        row[3] = vehicle.getCharacteristics();
        row[4] = String.valueOf(vehicle.getYearOfProduction());
        row[5] = String.valueOf(vehicle.getOdometer());
        row[6] = vehicle.getColor();
        row[7] = vehicle.getLicensePlate();
        row[8] = String.valueOf(vehicle.getNumberOfSeats());
        row[9] = vehicle.getPrice() != null && !vehicle.getPrice().toString().isEmpty()
                ? vehicle.getPrice().toString()
                : null;
        row[10] = String.valueOf(vehicle.getRequiredLicense());
        row[11] = vehicle.getBodyType() != null ? vehicle.getBodyType().toString() : null;

        return row;
    }

    public static String[] clientToCsv(Client client) {
        String[] row = new String[6];
        row[0] = String.valueOf(client.getId());
        row[1] = client.getEmail();
        row[2] = client.getPassword();
        row[3] = client.getName();
        row[4] = client.getBalance() != null && !client.getBalance().toString().isEmpty()
                ? client.getBalance().toString()
                : null;
        row[5] = client.getDriverLicense();
        return row;
    }

    public static String[] employeeToCsv(Employee employee) {
        String[] row = new String[6];

        row[0] = String.valueOf(employee.getId());
        row[1] = employee.getEmail();
        row[2] = employee.getPassword();
        row[3] = employee.getName();
        row[4] = employee.getPhone();
        row[5] = employee.getDateOfBirth() != null && !employee.getDateOfBirth().toString().isEmpty()
                ? employee.getDateOfBirth().toString()
                : null;

        return row;
    }
}
