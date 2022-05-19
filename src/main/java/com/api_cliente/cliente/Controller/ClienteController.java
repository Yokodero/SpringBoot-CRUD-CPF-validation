package com.api_cliente.cliente.Controller;


import java.util.InputMismatchException;
import java.util.List;

import com.api_cliente.cliente.Entity.Cliente;
import com.api_cliente.cliente.Repository.ClienteRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor
@RequestMapping("/cliente")

public class ClienteController {
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }
    ClienteRepository clienteRepository;
    @PostMapping(value="inserir") 
    public Cliente saveClient(@RequestBody Cliente cliente)
    {
        String cpf = cliente.getCpf().replaceAll("\\.", "").replaceAll("\\-","");
        if(isCPF(cpf))
        {
            cliente.setCpf(cpf);
            return clienteRepository.save(cliente);
        }
        else
            return null;
    }
    @GetMapping(value="listar")
    public List<Cliente> getAllClient() {
        return clienteRepository.findAll();
    }
    @GetMapping("obter/{id}")
    public Cliente getClienteById(@PathVariable Long id)
    {
        return clienteRepository.findById(id).get();
    }
    @PutMapping(value="alterar")
    public String updateClient(@RequestBody Cliente cliente)
    {
        
        String cpf = cliente.getCpf().replaceAll("\\.", "").replaceAll("\\-","");
        if(cliente.getId()>0&&isCPF(cpf))
        {
            cliente.setCpf(cpf);
            clienteRepository.save(cliente);
            return "atualizado";
        }
        else
            return "não atualizado";

    }
    @DeleteMapping("deletar/{id}")
    public void deleteClient(@PathVariable Long id)
    {
        if(id>0)
        {
            clienteRepository.deleteById(id);
        }
    }
    

    
    
}