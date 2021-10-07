package com.maher.currencyconverter.domain.useCases

import com.maher.currencyconverter.common.Resource
import com.maher.currencyconverter.domain.model.CurrencyComparator
import com.maher.currencyconverter.domain.model.CurrencySymbol
import com.maher.currencyconverter.domain.repository.CurrencyRepository
import com.maher.currencyconverter.domain.wrappers.toCurrencyComparator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CompareTwoCurrencies @Inject constructor(private val currencyRepository: CurrencyRepository) {

    operator fun invoke(
        fromCurrency: CurrencySymbol,
        toCurrency: CurrencySymbol,
        amount: Double
    ): Flow<Resource<CurrencyComparator>> = flow {
        try {
            emit(Resource.Loading<CurrencyComparator>())
            emit(
                Resource.Success(
                    currencyRepository.compare(
                        from = fromCurrency,
                        to = toCurrency,
                        amount = amount
                    ).response.toCurrencyComparator()
                )
            )
        } catch (e: HttpException) {
            //TODO handle all possible scenarios for error codes (400, 402, 404...)
            emit(Resource.Error<CurrencyComparator>(e.localizedMessage ?: "An unexpected error occurred"))
        }catch(e: IOException) {
            emit(Resource.Error<CurrencyComparator>("Having trouble connecting with the server"))
        }
    }

}