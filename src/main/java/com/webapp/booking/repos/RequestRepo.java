package com.webapp.booking.repos;

import com.webapp.booking.entities.RequestEntity;
import com.webapp.booking.enums.RequestStatus;
import com.webapp.booking.repos.util.RequestEntityRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RequestRepo {

    JdbcTemplate jdbcTemplate;
    RequestEntityRowMapper requestEntityRowMapper;

    public List<RequestEntity> getAllRequests() {
        String sql = "select hotel.hotel_id, hotel.name, hotel.address, hotel.rating, hotel.description, hotel.owner_id,\n" +
                "       room.room_id, room.number, room.guest_amount, room.room_type, room.price, room.description, room.discount,\n" +
                "       request.request_id, request.check_in, request.check_out, request.status, request.request_date,\n" +
                "       user.user_id, user.login, user.password, user.name, user.role\n" +
                "from hotel\n" +
                "join room on room.hotel_id = hotel.hotel_id\n" +
                "join request on room.room_id = request.room_id\n" +
                "join user on user.user_id = request.user_id";
        return jdbcTemplate.query(sql, requestEntityRowMapper);
    }

    public List<RequestEntity> getAllRequestsByUserID(Integer userID) {
        String sql = "select hotel.hotel_id, hotel.name, hotel.address, hotel.rating, hotel.description, hotel.owner_id,\n" +
                "       room.room_id, room.number, room.guest_amount, room.room_type, room.price, room.description, room.discount,\n" +
                "       request.request_id, request.check_in, request.check_out, request.status, request.request_date,\n" +
                "       user.user_id, user.login, user.password, user.name, user.role\n" +
                "from hotel\n" +
                "join room on room.hotel_id = hotel.hotel_id\n" +
                "join request on room.room_id = request.room_id\n" +
                "join user on user.user_id = request.user_id "
                + "WHERE user.user_id = " + userID;
        return jdbcTemplate.query(sql, requestEntityRowMapper);
    }

    public List<RequestEntity> getRequestByID(Integer requestID) {
        String sql = "select hotel.hotel_id, hotel.name, hotel.address, hotel.rating, hotel.description, hotel.owner_id,\n" +
                "       room.room_id, room.number, room.guest_amount, room.room_type, room.price, room.description, room.discount,\n" +
                "       request.request_id, request.check_in, request.check_out, request.status, request.request_date,\n" +
                "       user.user_id, user.login, user.password, user.name, user.role\n" +
                "from hotel\n" +
                "join room on room.hotel_id = hotel.hotel_id\n" +
                "join request on room.room_id = request.room_id\n" +
                "join user on user.user_id = request.user_id "
                + "WHERE request.request_id = " + requestID;
        sql += " ORDER BY request.request_date";
        return jdbcTemplate.query(sql, requestEntityRowMapper);
    }

    public void deleteRequest(Integer requestID) {
        String sql = "DELETE FROM request WHERE request_id = " + requestID;
        jdbcTemplate.update(sql);
    }

    public void createRequest(RequestEntity requestEntity) {
        String sql = "INSERT INTO request (room_id, user_id, check_in, check_out, request_date) VALUES ('"
                + requestEntity.getRoom().getRoomID() + "', '"
                + requestEntity.getUser().getUserID() + "', '"
                + new java.sql.Date(requestEntity.getCheckInDate().getTime()) + "', '"
                + new java.sql.Date(requestEntity.getCheckOutDate().getTime()) + "', "
                + "now())";
        jdbcTemplate.execute(sql);
    }

    public void updateRequest(RequestEntity requestEntity) {
        String sql = "UPDATE request SET "
                + "check_in = '" + requestEntity.getCheckInDate()
                + "', check_out = '" + requestEntity.getCheckOutDate()
                + "', status = '" + requestEntity.getRequestStatus()
                + "' WHERE room_id = " + requestEntity.getRoom().getRoomID();
        jdbcTemplate.execute(sql);
    }

    public void payRequest(Integer requestID) {
        String sql = "UPDATE request SET status = 'PAYED' WHERE request_id = " + requestID;
        jdbcTemplate.execute(sql);
    }

    public void rejectRequest(Integer requestID) {
        String sql = "UPDATE request SET status = 'REJECTED' WHERE request_id = " + requestID;
        jdbcTemplate.execute(sql);
    }

    public void approveRequest(Integer requestID) {
        String sql = "UPDATE request SET status = 'APPROVED' WHERE request_id = " + requestID;
        jdbcTemplate.execute(sql);
    }

    public List<RequestEntity> getAllRequestsWithFilter(String name, RequestStatus requestStatus) {
        String sql = "select hotel.hotel_id, hotel.name, hotel.address, hotel.rating, hotel.description, hotel.owner_id,\n" +
                "       room.room_id, room.number, room.guest_amount, room.room_type, room.price, room.description, room.discount,\n" +
                "       request.request_id, request.check_in, request.check_out, request.status, request.request_date,\n" +
                "       user.user_id, user.login, user.password, user.name, user.role\n" +
                "from hotel\n" +
                "join room on room.hotel_id = hotel.hotel_id\n" +
                "join request on room.room_id = request.room_id\n" +
                "join user on user.user_id = request.user_id ";

        if (name.isEmpty() && requestStatus == null) {
            return jdbcTemplate.query(sql, requestEntityRowMapper);
        }

        sql += "WHERE";

        if (!name.isEmpty()) {
            sql += " user.name = '" + name + "' AND";
        }
        if (requestStatus != null) {
            sql += " request.status = '" + requestStatus + "'";
        }

        if (sql.endsWith("WHERE") || sql.endsWith("AND")) {
            sql = sql.substring(0, sql.lastIndexOf(" "));
        }

        return jdbcTemplate.query(sql, requestEntityRowMapper);

    }

    public List<RequestEntity> getAllRequestsByRoomID(Integer roomID) {
        String sql = "select hotel.hotel_id, hotel.name, hotel.address, hotel.rating, hotel.description, hotel.owner_id,\n" +
                "       room.room_id, room.number, room.guest_amount, room.room_type, room.price, room.description, room.discount,\n" +
                "       request.request_id, request.check_in, request.check_out, request.status, request.request_date,\n" +
                "       user.user_id, user.login, user.password, user.name, user.role\n" +
                "from hotel\n" +
                "join room on room.hotel_id = hotel.hotel_id\n" +
                "join request on room.room_id = request.room_id\n" +
                "join user on user.user_id = request.user_id "
                + "WHERE room.room_id = " + roomID;
        return jdbcTemplate.query(sql, requestEntityRowMapper);
    }
}
