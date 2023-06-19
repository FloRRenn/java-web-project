package cinema.ticket.booking.utils;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomUUIDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return generateCustomUUID();
	}
	
	private String generateCustomUUID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            String uuidSegment = UUID.randomUUID().toString().replace("-", "");
            sb.append(uuidSegment.substring(0, 8));
            
            if (i < 2) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

}
