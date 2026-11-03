package org.JamesWeb.service.admin;

import org.JamesWeb.DTO.PaginationTransactionDetails;
import org.JamesWeb.DTO.TransactionDto;
import org.JamesWeb.DTO.adminResponseDtos.AdminMerchantDetailsDto;
import org.JamesWeb.DTO.adminResponseDtos.AdminResponseDto;
import org.JamesWeb.mapper.AdminMerchantDetailsMapper;
import org.JamesWeb.mapper.TransactionMapper;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.TransactionStatus;
import org.JamesWeb.repository.MerchantRepository;
import org.JamesWeb.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class Dashboard {
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private AdminMerchantDetailsMapper adminMerchantDetailsMapper;
    public AdminResponseDto<TransactionDto,AdminMerchantDetailsDto> dashboardResponse(){
        AdminResponseDto<TransactionDto,AdminMerchantDetailsDto> adminResponseDto = new AdminResponseDto<>();
        Long totalMerchants = merchantRepository.count();
        Long activeMerchants = merchantRepository.countByStatus("Approved");
        Long totalPayments = transactionDetailsRepository.count();
        Long successFulPayments = transactionDetailsRepository.countByStatus(TransactionStatus.APPROVED);
        Long failedPayments = transactionDetailsRepository.countByStatus(TransactionStatus.FAILED);
        Long pendingPayments = transactionDetailsRepository.countByStatus(TransactionStatus.WAITING);
        BigDecimal totalRevenue = transactionDetailsRepository.getTotalTransactionAmount();

        LocalDate today = LocalDate.now();

        List<TransactionDetails> transactions =
                transactionDetailsRepository.findByCreatedAtBetween(
                        today.atStartOfDay(),
                        today.plusDays(1).atStartOfDay()
                );
        Long todayTransactions = (long) transactions.size();
        BigDecimal todayRevenue = transactionDetailsRepository.getTodayTransactionAmount(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay());
        Pageable pageable = PageRequest.of(0,10);
        Page<TransactionDetails> transactionDetailsPage = transactionDetailsRepository.findAll(pageable);
        Page<TransactionDto> transactionDtoPage = transactionDetailsPage.map(transactionMapper::toDto);        ;
        Page<MerchantDetails> adminMerchantDetailsPage = merchantRepository.findAll(pageable);
        Page<AdminMerchantDetailsDto> merchantDetailsDtos = adminMerchantDetailsPage.map(adminMerchantDetailsMapper::adminMerchantDetailsDto);
        PaginationTransactionDetails<AdminMerchantDetailsDto> adminPaginationTransactionDetails = PaginationTransactionDetails.from(merchantDetailsDtos);
        adminResponseDto.setAdminsPaginationMerchantDetails(adminPaginationTransactionDetails);
        adminResponseDto.setPaginationTransactionDetails(PaginationTransactionDetails.from(transactionDtoPage));
        adminResponseDto.setTotalMerchants(totalMerchants);
        adminResponseDto.setActiveMerchants(activeMerchants);
        adminResponseDto.setTotalPayments(totalPayments);
        adminResponseDto.setSuccessfulPayments(successFulPayments);
        adminResponseDto.setFailedPayments(failedPayments);
        adminResponseDto.setPendingPayments(pendingPayments);
        adminResponseDto.setTodayRevenue(todayRevenue);
        adminResponseDto.setTotalRevenue(totalRevenue);
        adminResponseDto.setTodayTransactions(todayTransactions);
        return adminResponseDto;
    }
}
