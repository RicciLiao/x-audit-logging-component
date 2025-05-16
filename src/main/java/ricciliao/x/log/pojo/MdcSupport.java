package ricciliao.x.log.pojo;

import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class MdcSupport implements Serializable {
    @Serial
    private static final long serialVersionUID = -4503695941192642788L;

    private final String transactionId;
    private final String operation;

    public MdcSupport(@NonNull String transactionId, @NonNull String operation) {
        this.transactionId = transactionId;
        this.operation = operation;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdcSupport mdcBo)) return false;
        return Objects.equals(getTransactionId(), mdcBo.getTransactionId()) && Objects.equals(getOperation(), mdcBo.getOperation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getOperation());
    }

}
