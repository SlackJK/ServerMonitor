import pandas as pd
import dash
from dash import html, dcc, callback, Input, Output
import plotly.express as px

# define page based on file name
dash.register_page(__name__)

# Create data
data = {
    "time":[0,1,2,3,4,5],
    "temp":[50,54,60,70,20,60]
}
df = pd.DataFrame(data)

# Create graph
fig = px.line(df, x="time", y="temp")

# Html that gets rendered below the page header
layout = html.Div([
    html.H1("this is a temp test page"),
    html.P("this page is for anton to fuck around on"),
    # Graph div
    html.Div(
        dcc.Graph(figure=fig)
    )
])
