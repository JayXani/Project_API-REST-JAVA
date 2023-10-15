package br.com.apirest.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Util {
    //Esse método copia tudo que não for "nulo" do objeto
    //Source é o objeto de origem, target é para o objeto de destino
    public static void copyNonNUllPropeties(Object source, Object target){
        //Abaixo ele está copiando as propriedades do objeto de origem(source) e jogando no objeto de destino(targe)
        //O 3º parametro é a "regra" que ele tem que seguir para realizar essa cópia
        //Ou seja, ele vai verificar toda chave que tiver o valor nulo em target, fazer uma cópia do valor que
        //corresponde a mesma chave em source e jogar em target.
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    //O método abaixo pega todas as propriedades (chaves do objeto)
    // E verifica se o valor deles é nulo
    public static String[] getNullPropertyNames(Object source) {
        //BeanWrapper é uma interface que me permite acessar propriedades de um objeto
        //BeanWrapperImpl é a implementação dessa interface, eu envio para o construtor
        // O objeto que eu quero ter acesso às propriedades.
        // src vira um objeto "cópia" de source
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();//Esse método de "src" pega todas as propriedades do objeto e retorna um array

        //"Lista" que vai conter os nomes das chaves (propriedades) que possuem valores nulos
        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd : pds){ // Aqui estou percorrendo o array de propriedades
            // srcValue está recebendo o valor da propriedade que está no objeto "src' cuja a chave seja igual
            // A chave que está em pd.
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        //O result é só para estipular um tamanho para a conversão do "emptyNames"
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
