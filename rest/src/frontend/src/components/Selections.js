import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';

export default function Selections() {
    const PaperStyle={padding: '20px 50px', margin:'100px auto', width:600, height:400}
    const ButtonStyle={padding: '15px 20px', margin: '20px auto'}
    const [city, setCity] = React.useState('');
    const [vehicle, setVehicle] = React.useState('');

    const handleChangeForCity = (event: SelectChangeEvent) => {
        setCity(event.target.value);
    };
    const handleChangeForVehicle = (event: SelectChangeEvent) => {
        setVehicle(event.target.value);
    };

    var result;
    var jsonresult;
    const [deliveryFee, setDeliveryFee] = React.useState('');



    const handleClick = async (e: SelectChangeEvent) => {
        e.preventDefault()
        const deliveryData = {city, vehicle}
        console.log(deliveryData)
        try {
            result = await fetch("http://localhost:8080/delivery", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(deliveryData)
            })
            console.log(result)
            jsonresult = await result.json()
            setDeliveryFee("Delivery fee: " + jsonresult + "€")
            console.log("Delivery fee: " + deliveryFee)
        } catch (error) {
            jsonresult = "Usage of selected vehicle type is forbidden"
            setDeliveryFee(jsonresult)
        }
    }

    return (
        <Container>
            <Paper elevation={3} style={PaperStyle}>
                <h1>Calculate food delivery</h1>
        <div>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
                <InputLabel>City</InputLabel>
                <Select
                    value={city}
                    label="City"
                    onChange={handleChangeForCity}
                >
                    <MenuItem value="">
                    </MenuItem>
                    <MenuItem value="Tallinn">Tallinn</MenuItem>
                    <MenuItem value="Tartu">Tartu</MenuItem>
                    <MenuItem value="Pärnu">Pärnu</MenuItem>
                </Select>
                <FormHelperText>Choose city for delivery</FormHelperText>
            </FormControl>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
                <InputLabel>Vehicle</InputLabel>
                <Select
                    value={vehicle}
                    label="Vehicle"
                    onChange={handleChangeForVehicle}
                >
                    <MenuItem value="">
                    </MenuItem>
                    <MenuItem value="Car">Car</MenuItem>
                    <MenuItem value="Scooter">Scooter</MenuItem>
                    <MenuItem value="Bike">Bike</MenuItem>
                </Select>
                <FormHelperText>Choose vehicle type for delivery</FormHelperText>
            </FormControl>
        </div>
                <Button disabled={city==='' || vehicle===''} variant="contained" style={ButtonStyle} onClick={handleClick}>Calculate</Button>
                <div>
                    <h2>{deliveryFee}</h2>
                </div>
            </Paper>
        </Container>
    );
}