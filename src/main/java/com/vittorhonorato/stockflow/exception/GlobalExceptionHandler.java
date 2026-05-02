package com.vittorhonorato.stockflow.exception;

import com.vittorhonorato.stockflow.exception.categorias.CategoriaDuplicadaException;
import com.vittorhonorato.stockflow.exception.categorias.CategoriaNaoEncontradaException;
import com.vittorhonorato.stockflow.exception.estoques.EstoqueInsuficienteException;
import com.vittorhonorato.stockflow.exception.estoques.ProdutoInativoParaMovimentacaoException;
import com.vittorhonorato.stockflow.exception.fornecedores.DocumentoFornecedorDuplicadoException;
import com.vittorhonorato.stockflow.exception.fornecedores.DocumentoFornecedorInvalidoException;
import com.vittorhonorato.stockflow.exception.fornecedores.FornecedorNaoEncontradoException;
import com.vittorhonorato.stockflow.exception.fornecedores.SituacaoCadastralFornecedorInvalidaException;
import com.vittorhonorato.stockflow.exception.integration.CnpjaBadGatewayException;
import com.vittorhonorato.stockflow.exception.integration.CnpjaServiceUnavailableException;
import com.vittorhonorato.stockflow.exception.produtos.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoriaNaoEncontrada(
            CategoriaNaoEncontradaException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(CategoriaDuplicadaException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoriaDuplicada(
            CategoriaDuplicadaException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(FornecedorNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> handleFornecedorNaoEncontrado(
            FornecedorNaoEncontradoException exception,
            HttpServletRequest request
    ) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(DocumentoFornecedorInvalidoException.class)
    public ResponseEntity<ApiErrorResponse> handleDocumentoInvalido(
            DocumentoFornecedorInvalidoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(DocumentoFornecedorDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> handleDocumentoDuplicado(
            DocumentoFornecedorDuplicadoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(SituacaoCadastralFornecedorInvalidaException.class)
    public ResponseEntity<ApiErrorResponse> handleSituacaoCadastralInvalida(
            SituacaoCadastralFornecedorInvalidaException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoNaoEncontrado(
            ProdutoNaoEncontradoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoSkuDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoSkuDuplicado(
            ProdutoSkuDuplicadoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoInvalido(
            ProdutoInvalidoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoCategoriaInativaException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoCategoriaInativa(
            ProdutoCategoriaInativaException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoFornecedorInativoException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoFornecedorInativo(
            ProdutoFornecedorInativoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ApiErrorResponse> handleEstoqueInsuficiente(
            EstoqueInsuficienteException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(ProdutoInativoParaMovimentacaoException.class)
    public ResponseEntity<ApiErrorResponse> handleProdutoInativoParaMovimentacao(
            ProdutoInativoParaMovimentacaoException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(CnpjaBadGatewayException.class)
    public ResponseEntity<ApiErrorResponse> handleCnpjaBadGateway(
            CnpjaBadGatewayException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_GATEWAY,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(CnpjaServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleCnpjaServiceUnavailable(
            CnpjaServiceUnavailableException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos campos enviados",
                request.getRequestURI(),
                details
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        String message = "JSON inválido ou valor incompatível com o tipo esperado";
        List<String> details = List.of("Verifique o corpo da requisição");

        Throwable cause = exception.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            Class<?> targetType = invalidFormatException.getTargetType();

            if (targetType != null && targetType.isEnum()) {
                Object[] acceptedValues = targetType.getEnumConstants();

                message = "Valor inválido para o enum " + targetType.getSimpleName()
                        + ". Valores aceitos: " + List.of(acceptedValues);

                details = List.of("Valor enviado: " + invalidFormatException.getValue());
            }
        }

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI(),
                details
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno inesperado",
                request.getRequestURI(),
                List.of(exception.getMessage())
        );
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            String path,
            List<String> details
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                details
        );

        return ResponseEntity.status(status).body(response);
    }
}
