package edu.uoc.android.pec3app

import edu.uoc.android.pec3app.models.Museums
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MuseumService {
    //REST call type GET
    @GET("/api/dataset/museus/format/json/pag-ini/{pagIni}/pag-fi/{pagFi}")
    fun museums(
        @Path("pagIni") pagIni: String,
        @Path("pagFi") pagFi: String
    ) : Call<Museums>
}