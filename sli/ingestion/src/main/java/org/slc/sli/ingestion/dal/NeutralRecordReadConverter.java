package org.slc.sli.ingestion.dal;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.slc.sli.dal.encrypt.EntityEncryption;
import org.slc.sli.ingestion.NeutralRecord;

/**
 * Spring converter registered in the Mongo configuration to convert DBObjects into MongoEntity.
 *
 */
public class NeutralRecordReadConverter implements Converter<DBObject, NeutralRecord> {

    @Autowired(required = false)
    private EntityEncryption encryptor;

    public void setEncryptor(EntityEncryption encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NeutralRecord convert(DBObject dbObj) {

        String type = dbObj.get("type").toString();
        Map<?, ?> map = dbObj.toMap();
        Map<String, Object> encryptedBody = new HashMap<String, Object>();
        if (map.containsKey("body")) {
            encryptedBody.putAll((Map<String, ?>) map.get("body"));
        }

        // Decrypt the neutral record from datastore persistence. TODO: Create a generic encryptor!
        Map<String, Object> body = encryptedBody;
        if (encryptor != null) {
            body = encryptor.decrypt(type, encryptedBody);
        }

        String id = body.get("localId").toString();
        body.remove("localId");

        NeutralRecord neutralRecord = new NeutralRecord();
        neutralRecord.setLocalId(id);
        neutralRecord.setRecordType(type);
        neutralRecord.setAttributes(body);
        return neutralRecord;
    }

}
