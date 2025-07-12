package com.example.procurehub.service;

import com.example.jooq.tables.pojos.Lot;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.jooq.Tables.LOT;

@Service
public class LotService {
    private final DSLContext dsl;

    public LotService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Lot> findAll() {
        return dsl.selectFrom(LOT)
                .fetchInto(Lot.class);
    }

    public Optional<Lot> findById(Integer id) {
        return dsl.selectFrom(LOT)
                .where(LOT.ID.eq(id))
                .fetchOptionalInto(Lot.class);
    }

    public Lot save(Lot lot) {
        dsl.insertInto(LOT)
                .set(dsl.newRecord(LOT, lot))
                .execute();
        return lot;
    }

    public Optional<Lot> update(Integer id, Lot lot) {
        int updated = dsl.update(LOT)
                .set(dsl.newRecord(LOT, lot))
                .where(LOT.ID.eq(id))
                .execute();
        return updated == 1 ? Optional.of(lot) : Optional.empty();
    }

    public boolean delete(Integer id) {
        return dsl.deleteFrom(LOT)
                .where(LOT.ID.eq(id))
                .execute() == 1;
    }
}
