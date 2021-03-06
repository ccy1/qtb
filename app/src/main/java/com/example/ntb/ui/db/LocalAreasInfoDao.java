package com.example.ntb.ui.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.example.ntb.ui.city.bean.LocalAreasInfo;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "LOCAL_AREAS_INFO".
*/
public class LocalAreasInfoDao extends AbstractDao<LocalAreasInfo, Long> {

    public static final String TABLENAME = "LOCAL_AREAS_INFO";

    /**
     * Properties of entity LocalAreasInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AreasId = new Property(1, String.class, "areasId", false, "AREAS_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Pinyin = new Property(3, String.class, "pinyin", false, "PINYIN");
        public final static Property Pid = new Property(4, String.class, "pid", false, "PID");
        public final static Property X = new Property(5, String.class, "x", false, "X");
        public final static Property Y = new Property(6, String.class, "y", false, "Y");
    }


    public LocalAreasInfoDao(DaoConfig config) {
        super(config);
    }
    
    public LocalAreasInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_AREAS_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"AREAS_ID\" TEXT," + // 1: areasId
                "\"NAME\" TEXT," + // 2: name
                "\"PINYIN\" TEXT," + // 3: pinyin
                "\"PID\" TEXT," + // 4: pid
                "\"X\" TEXT," + // 5: x
                "\"Y\" TEXT);"); // 6: y
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_AREAS_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalAreasInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String areasId = entity.getAreasId();
        if (areasId != null) {
            stmt.bindString(2, areasId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(4, pinyin);
        }
 
        String pid = entity.getPid();
        if (pid != null) {
            stmt.bindString(5, pid);
        }
 
        String x = entity.getX();
        if (x != null) {
            stmt.bindString(6, x);
        }
 
        String y = entity.getY();
        if (y != null) {
            stmt.bindString(7, y);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalAreasInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String areasId = entity.getAreasId();
        if (areasId != null) {
            stmt.bindString(2, areasId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(4, pinyin);
        }
 
        String pid = entity.getPid();
        if (pid != null) {
            stmt.bindString(5, pid);
        }
 
        String x = entity.getX();
        if (x != null) {
            stmt.bindString(6, x);
        }
 
        String y = entity.getY();
        if (y != null) {
            stmt.bindString(7, y);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalAreasInfo readEntity(Cursor cursor, int offset) {
        LocalAreasInfo entity = new LocalAreasInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // areasId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pinyin
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // x
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // y
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalAreasInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAreasId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPinyin(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setX(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setY(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalAreasInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalAreasInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalAreasInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
