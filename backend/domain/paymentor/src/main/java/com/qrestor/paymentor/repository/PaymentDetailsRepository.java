package com.qrestor.paymentor.repository;

import com.qrestor.paymentor.entity.PaymentRequestDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends CrudRepository<PaymentRequestDetails, UUID> {
}
